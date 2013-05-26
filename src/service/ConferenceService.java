package service;

import java.util.List;

import model.Conference;
import model.Paper;
import common.ReferenceObject;
import dao.CategoryDAO;
import dao.ConferenceDAO;

public class ConferenceService {
  
  private ConferenceDAO conferenceDao;
  private CategoryDAO categoryDao;
  
  private ConferenceService() {
    conferenceDao = new ConferenceDAO();
    categoryDao = new CategoryDAO();
  }
  
  /**
   * Saves conference object to data source.
   * @param aConference The conference object to save.
   */
  public void saveConference(final Conference aConference) {
    conferenceDao.saveConference(aConference);
  }
  
  /**
   * Add a paper to the conference.
   * @param the_paper the paper to add to the event.
   */
  public void addPaper(final Paper the_paper, final int aConfId)
  {
    conferenceDao.addPaper(aConfId, the_paper); 
  }
  
  /**
   * Returns a list of conferences.
   * @return a list of conferences.
   * @author Danielle (refactor)
   */
  public List<Conference> getConferences() {
    return conferenceDao.getConferences();
  }
  
  /**
   * Returns a single conference.
   */
  public Conference getConference(final int aConfId) {
    return conferenceDao.getConference(aConfId);
  }
  
  /**
   * Returns a list of categories.
   * @return Returns a list of categories.
   */
  public List<ReferenceObject> getCategories() {
    return categoryDao.getCategoriesRef();
  }
  
  /**
   * Returns ConferenceService instance.
   * @return Returns ConferenceService instance.
   */
  public static ConferenceService getInstance() {
    return new ConferenceService();
  }
}
