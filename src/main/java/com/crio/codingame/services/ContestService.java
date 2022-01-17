package com.crio.codingame.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.crio.codingame.dtos.ContestSummaryDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.entities.ScoreWeight;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidContestException;
import com.crio.codingame.exceptions.QuestionNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IQuestionRepository;
import com.crio.codingame.repositories.IUserRepository;


public class ContestService implements IContestService {
    private final IContestRepository contestRepository;
    private final IQuestionRepository questionRepository;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public ContestService(IContestRepository contestRepository, IQuestionRepository questionRepository,
            IUserRepository userRepository, IUserService userService) {
        this.contestRepository = contestRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Contest create(String contestName, Level level, String contestCreator, Integer numQuestion) throws UserNotFoundException, QuestionNotFoundException {
        final User user = userRepository.findByName(contestCreator).orElseThrow(() -> new UserNotFoundException("Cannot Create Contest. Contest Creator for given name: " + contestCreator + " not found!"));
        final List<Question> questions = questionRepository.findAllQuestionLevelWise(level);
        if(questions.isEmpty()){
            throw new QuestionNotFoundException("Cannot create Contest. Enough number of questions can not found. Please try again later!");
        }
        if(numQuestion == null || numQuestion == 0 || questions.size() <= numQuestion){
            Contest contest = contestRepository.save(new Contest(contestName, questions,level,user,ContestStatus.NOT_STARTED));
            userService.attendContest(contest.getId(),contestCreator);
            return contest;
        }
        List<Question> filteredQuestionList = pickQuestionsList(questions, numQuestion);
        Contest contest = contestRepository.save(new Contest(contestName, filteredQuestionList,level,user,ContestStatus.NOT_STARTED));
        userService.attendContest(contest.getId(),contestCreator);
        return contest;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Return a specific List of Random Questions as specified by numQuestion.

    private List<Question> pickQuestionsList(final List<Question> questions,final Integer numQuestion){
     return Collections.emptyList();
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Contest if level is not specified.
    // Or
    // Get List of Contest which matches the level provided.

    @Override
    public List<Contest> getAllContestLevelWise(Level level) {
     return Collections.emptyList();
    }

    @Override
    public ContestSummaryDto runContest(String contestId, String contestCreator) throws ContestNotFoundException, InvalidContestException {
        final Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Run Contest. Contest for given id:"+contestId+" not found!"));
        validateContest(contest, contestCreator);
        final String contestLevel = contest.getLevel().toString();
        final int scoreWeight = ScoreWeight.valueOf(contestLevel).getWeight();
        final List<User> allContestUser = userRepository.findAll().stream().filter(u -> u.checkIfContestExists(contest)).collect(Collectors.toList());
        List<Question> qList = contest.getQuestions();
        Contest clonedContest = new Contest(contest);
        clonedContest.endContest();
        List<User> userResultList = new ArrayList<>();
        allContestUser.forEach(user ->{
            final List<Question> solvedQuestions = pickRandomQuestions(qList);
            User userNewScore = calculateUserNewScore(scoreWeight,user,solvedQuestions);
            userNewScore.addContestQuestion(clonedContest, solvedQuestions);
            userResultList.add(userNewScore);
            userRepository.save(userNewScore);
        });
        contest.endContest();
        Contest endedContest = contestRepository.save(contest);
       return new ContestSummaryDto(endedContest,userResultList);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Throw InvalidContestException for invalid cases.
    // Hint :- Refer Unit Testcases for runContest method
    
    private void validateContest(final Contest contest, final String contestCreator) throws InvalidContestException {
    }

    //Reference:- https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
    private List<Question> pickRandomQuestions(final List<Question> questions){
        List<Question> qList = questions.stream().collect(Collectors.toList());
        int size = qList.size();
        Random random = new Random();
        if(size <= 2){
            return questions;
        }
        int delta = random.nextInt(size - 2) + 1;
        final List<Question> newList = new ArrayList<Question>();
        for(int i=0; i < delta; i++ ){
            int randomIndex = random.nextInt(qList.size());
            newList.add(qList.get(randomIndex));
            qList.remove(randomIndex);
        }
        return newList;
    }

    private User calculateUserNewScore(int scoreWeight, User user, List<Question> qList){
        final Integer calculatedScore = qList.stream().map(q -> q.getScore()).reduce(0,Integer::sum);
        final Integer currentScore = user.getScore();
        final Integer newScore = currentScore + calculatedScore - scoreWeight;
        return new User(user.getId(),user.getName(),newScore,user.getContests());
    }
    
}
