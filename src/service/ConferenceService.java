package service;

import java.util.List;

import model.Conference;

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
   * Returns a list of conferences.
   * @return a list of conferences.
   * @author Danielle (refactor)
   */
  public List<Conference> getConferences() {
    return conferenceDao.getConferences();
  }
  
  /**
   * Returns a single conference.
   * @param the_conf_id the id of the conference.
   */
  public Conference getConference(final int the_conf_id) {
	  Conference conf = conferenceDao.getConference(the_conf_id);
	  conf.setCategories(conferenceDao.getCategories(the_conf_id));
    return conf;
  }
  
  /**
   * Returns a list of all system-wide categories for making a new conference.
   * @return Returns a list of categories.
   */
  public List<String> getCategories() {
    return categoryDao.getCategories();
  }
  
  
  /**
   * Returns ConferenceService instance.
   * @return Returns ConferenceService instance.
   */
  public static ConferenceService getInstance() {
    return new ConferenceService();
  }
}
