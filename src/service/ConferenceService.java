package service;

import java.util.List;

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
   * Returns a list of conferences.
   * @return a list of conferences.
   */
  public List<ReferenceObject> getConferences() {
    return conferenceDao.getConferencesRef();
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
