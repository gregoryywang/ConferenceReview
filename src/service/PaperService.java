package service;

import java.util.List;

import model.Paper;
import model.Recommendation;
import model.Review;
import model.Reviewer;
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
	  List<Paper> papers = paperDao.getPapers(aUserId, aRole, aConfId);
	  for(Paper paper: papers)
	  {
		  paper.setReview(paperDao.getReviews(paper.getID()));
	  }
    return papers;
  }
  
  /**
   * Submits paper review.
   */
  public void addReview(final Review aReview, final Paper the_paper) 
  {
	  
	  paperDao.addReview(aReview, the_paper.getID());
  }
  
  /**
   * Get all reviews associated with this paper.
   * @param aPaperId the ID of the paper
   * @return All Reviews associated with this paper.  If none exist, an
   * empty list will be returned.
   */
  public List<Review> getReviews(final int aPaperId) {
    return paperDao.getReviews(aPaperId);
  }
  
  /**
   * Submits paper review.
   */
  public void addRecommendation(final Recommendation aRecommendation, final Paper aPaper) 
  {
	  aPaper.setRecommendation(aRecommendation);
	  paperDao.savePaper(aPaper);
  }
  
  /**
   * Access the recommendation for this paper
   * @param aPaperId the id of the paper
   * @return the recommendation for this paper.  If it does not exist, 
   * a default Recommendation will be returned.
   */
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
  
  /**
   * 
   * @param the_paper_id
   * @return the list of Reviewers associated with the paper. If no reviewers
   * are assigned, then returns an empty list.
   */
  public List<Reviewer> getAssignedReviewers(final int the_paper_id)
  {
	  return paperDao.getAssignedReviewers(the_paper_id);
  }
}
