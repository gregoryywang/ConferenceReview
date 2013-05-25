package service;

import java.util.ArrayList;
import java.util.List;

import model.Paper;
import model.Recommendation;
import model.Review;
import model.Role;
import dao.PaperDAO;

/**
 * Provides services related to papers.
 * @author Roshun Jones
 *
 */
public class PaperService {
  private PaperDAO paperDao;
  
  private PaperService() {
    paperDao = new PaperDAO();
  }
  
  /**
   * Saves a paper object.
   * @param aPaper The paper to save.
   */
  public void savePaper(final Paper aPaper){
    paperDao.savePaper(aPaper);
  }
  
  /**
   * Assigns a paper to a user.
   */
  public void assignPaper(final int aPaperId, 
                          final int aUserId, 
                          final int aConfId,
                          final Role aRole) {
    paperDao.assignPaper(aPaperId, aUserId, aConfId, aRole);
    
  }
  
  /**
   * Returns assigned papers.
   * @param aUserId The assignee's user id.
   * @param aConfId The conference id.
   * @param aRole The role.
   */
  public List<Paper> getAssignedPapers(final int aUserId, 
                                       final int aConfId, 
                                       final Role aRole) {
    return new ArrayList();
  }
  
  /**
   * Submits paper review.
   */
  public void addReview(final Review aReview, final int aPaperId) {
    
  }
  
  public List<Review> getReviews(final int aPaperId) {
    return new ArrayList();
  }
  
  /**
   * Submits paper review.
   */
  public void addRecommendation(final Recommendation aRecommendation, final int aPaperId) {
    
  }
  
  public List<Recommendation> getRecommendations(final int aPaperId) {
    return new ArrayList();
  }
  
  /**
   * Returns PaperService instance.
   * @return Returns PaperService instance.
   */
  public static PaperService getInstance() {
    return new PaperService();
  }
}
