package nocoders.courseworkdb.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import nocoders.courseworkdb.model.Question;
import nocoders.courseworkdb.model.QuestionForm;
import nocoders.courseworkdb.model.Result;
import nocoders.courseworkdb.repository.QuestionRepo;
import nocoders.courseworkdb.repository.ResultRepo;

@Service
public class QuizService {

    @Autowired
    Question question;
    @Autowired
    QuestionForm qForm;
    @Autowired
    QuestionRepo qRepo;
    @Autowired
    Result result;
    @Autowired
    ResultRepo rRepo;


//    public QuestionForm getQuestionsBySubjectId(int subject_id) {
//        List<Question> allQues = qRepo.findBySubjectId(subject_id);
//        List<Question> qList = new ArrayList<Question>();
//
//        Random random = new Random();
//
//        for(int i=0; i<5 && i<allQues.size(); i++) {
//            int rand = random.nextInt(allQues.size());
//            qList.add(allQues.get(rand));
//            allQues.remove(rand);
//        }
//
//        qForm.setQuestions(qList);
//
//        return qForm;
//    }


    public QuestionForm getQuestions() {
        List<Question> allQues = qRepo.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random random = new Random();

        for(int i=0; i<15; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        qForm.setQuestions(qList);

        return qForm;
    }

    public int getResult(QuestionForm qForm) {
        int correct = 0;

        for(Question q: qForm.getQuestions())
            if(q.getAns() == q.getChose())
                correct++;

        return correct;
    }

    public void saveScore(Result result) {
        Result saveResult = new Result();
        saveResult.setUsername(result.getUsername());
        saveResult.setTotalCorrect(result.getTotalCorrect());
        rRepo.save(saveResult);
    }

    public List<Result> getTopScore() {
        List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));

        return sList;
    }




    public List<Question> listAll() {
        return qRepo.findAll();
    }
    public void save(Question question) {
        qRepo.save(question);
    }

    public Question get(int quesId) {
        return qRepo.findById(quesId).get();
    }

    public void delete(int quesId) {
        qRepo.deleteById(quesId);
    }
}