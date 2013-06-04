package service;

import java.util.ArrayList;
import java.util.List;

import model.Paper;
import model.Recommendation;
import model.Review;
import model.Role;
import model.SubProgramChair;
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
    return paperDao.getPapers(aUserId, aRole, aConfId);
  }
  
  /**
   * Submits paper review.
   */
  public void addReview(final Review aReview, final Paper the_paper) 
  {
	  
	  paperDao.addReview(aReview, the_paper.getID());
  }
  
  public List<Review> getReviews(final int aPaperId) {
    return new ArrayList<Review>();
  }
  
  /**
   * Submits paper review.
   */
  public void addRecommendation(final Recommendation aRecommendation, final Paper aPaper) 
  {
	  aPaper.setRecommendation(aRecommendation);
	  paperDao.savePaper(aPaper);
  }
  
  public Recommendation getRecommendation(final int aPaperId) 
  {
	  return paperDao.getRecommendation(aPaperId);
  }
  
  public void deletePaper(final int the_paper_id)
  {
	  paperDao.deletePaper(the_paper_id);
  }
  
  /**
   * Returns PaperService instance.
   * @return Returns PaperService instance.
   */
  public static PaperService getInstance() {
    return new PaperService();
  }
  
  /**
   * 
   * @param the_paper_id the paper id
   * @return the user which is the subprogram chair for the paper. (If no
   * subprogram chair exists, the default SubprogramChair will be returned.
   */
  public SubProgramChair getAssignedSubprogramChair(final int the_paper_id)
  {
	  return paperDao.getAssignedSubProgramChair(the_paper_id);
  }
}
