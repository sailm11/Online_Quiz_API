package com.quizzy.v1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizzy.v1.dto.McqQuestionResponse;
import com.quizzy.v1.dto.TextQuestionResponse;
import com.quizzy.v1.dto.request.AnswerDto;
import com.quizzy.v1.dto.request.QuestionDto;
import com.quizzy.v1.dto.request.QuizDto;
import com.quizzy.v1.dto.request.TitleDto;
import com.quizzy.v1.dto.response.OptionResponseDto;
import com.quizzy.v1.dto.response.QuestionResponseBase;
import com.quizzy.v1.dto.response.QuizResponseDto;
import com.quizzy.v1.model.Answer;
import com.quizzy.v1.model.Question;
import com.quizzy.v1.model.Quiz;
import com.quizzy.v1.repository.AnswerRepository;
import com.quizzy.v1.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createQuizTitle(TitleDto title) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title.getTitle());
        quizRepository.save(quiz);
        return title + " Quiz Created.";
    }

    @Override
    public String createQuiz(QuizDto quizDto, Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + quizId));

        List<Question> questions = quizDto.getQuestion().stream().map(qDto -> {
            validateQuiz(qDto);

            Question question = new Question();
            question.setQuestionText(qDto.getQuestionText());
            question.setType(qDto.getAnswerType());
            question.setQuiz(quiz);

            if ("TEXT".equalsIgnoreCase(qDto.getAnswerType())) {
                question.setCorrectAnswer(qDto.getUserTextAnswer());
            } else {
                List<Answer> answers = qDto.getAnswers().stream().map(aDto -> {
                    Answer answer = new Answer();
                    answer.setAnswerText(aDto.getAnswerText());
                    answer.setCorrect(Boolean.TRUE.equals(aDto.isCorrect()));
                    answer.setQuestion(question);
                    return answer;
                }).collect(Collectors.toList());
                question.setAnswers(answers);
            }
            return question;
        }).collect(Collectors.toList());

        quiz.getQuestion().clear();
        quiz.getQuestion().addAll(questions);
        quizRepository.save(quiz);

        return "Quiz created successfully.";
    }

    private void validateQuiz(QuestionDto questionDto) {
        String type = questionDto.getAnswerType();
        if ("SINGLE".equalsIgnoreCase(type)) {
            long correctCount = questionDto.getAnswers().stream().filter(AnswerDto::isCorrect).count();
            if (correctCount != 1)
                throw new IllegalArgumentException("SINGLE choice must have exactly one correct answer.");
        } else if ("MULTIPLE".equalsIgnoreCase(type)) {
            long correctCount = questionDto.getAnswers().stream().filter(AnswerDto::isCorrect).count();
            if (correctCount < 1)
                throw new IllegalArgumentException("MULTIPLE choice must have at least one correct answer.");
        } else if ("TEXT".equalsIgnoreCase(type)) {
            String ans = questionDto.getUserTextAnswer();
            if (ans == null || ans.isBlank())
                throw new IllegalArgumentException("TEXT question must have a correct answer defined.");
            if (ans.length() > 300)
                throw new IllegalArgumentException("TEXT answer must not exceed 300 characters.");
        }
    }

    @Override
    public List<QuizResponseDto> getQuiz() {
        List<Quiz> quizResponse = quizRepository.findAll();
        {
            return quizResponse
                    .stream().map(quiz -> {
                        List<QuestionResponseBase> questionResponses = quiz.getQuestion().stream()
                                .<QuestionResponseBase>map(q -> {
                                    if ("TEXT".equalsIgnoreCase(q.getType())) {
                                        return new TextQuestionResponse(q.getId(), quiz.getTitle(),
                                                q.getQuestionText());
                                    } else {
                                        List<OptionResponseDto> options = q.getAnswers().stream()
                                                .map(a -> new OptionResponseDto(a.getAnswerText()))
                                                .collect(Collectors.toList());
                                        return new McqQuestionResponse(q.getId(), quiz.getTitle(), q.getQuestionText(),
                                                options);
                                    }
                                })
                                .collect(Collectors.toList());
                        return new QuizResponseDto(quiz.getId(), quiz.getTitle(), questionResponses);
                    }).collect(Collectors.toList());
        }
    }

    @Override
    public String deleteQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz is not found with quizId" + quizId));
        quizRepository.delete(quiz);
        return "Deleted successfully.";
    }

}