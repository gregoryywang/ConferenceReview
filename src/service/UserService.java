package service;

import java.util.List;

import model.Paper;
import model.Role;
import model.User;
import dao.UserDAO;

/**
 * Service to talk with the data access objects about users.
 * @author Roshun Jones
 * @version 2013 Spring
 */
public class UserService {
	/**
	 * The userDAO to use for connecting to the data.
	 */
	private final UserDAO userDao;

	/**
	 * Private constructor prevents public instantiation.
	 */
	private UserService() {
		userDao = new UserDAO();
	}

	/**
	 * Authenticates user based on username and password.
	 * @param userName The username credential.
	 * @param password The password credential.
	 * @return null or User object.
	 */
	public User authenticateUser(final String aUserName, final String aPassword) {
		return userDao.authenticate(aUserName, aPassword);
	}

	/**
	 * Checks whether a user with the provided userid is an Admin.
	 * @param aUserid The user's id.
	 * @return true or false.
	 */
	public boolean isAdmin(final int aUserid) {
		return userDao.isAdmin(aUserid);
	}

	/**
	 * Get a list of Roles associated with a user and conference.
	 * @param the_user_id the user id.
	 * @param the_conf_id the conference id.
	 * @return a list of Roles associated with a user and conference.
	 * @author Danielle
	 */
	public List<Role> getRoles(final int the_user_id, final int the_conf_id)
	{
		return userDao.getRoles(the_user_id, the_conf_id);
	}

	/**
	 * Get all users which are not admins.
	 * @return a list of all users who are not adminstrators.
	 */
	public List<User> getAllUsers()
	{
		return userDao.getUsers();
	}

	/**
	 * Get all users who are eligible for assignment in the
	 * given role for the given paper
	 * @param the_paper the paper
	 * @param conf_id the id of the conference associated with the paper
	 * @param the_role the role type for assignment to the paper.
	 * Valid roles which return users other than from getAllUsers() are Reviewer
	 * and SubProgramChair
	 * @return a list of all users who are available to fill the role
	 * for the given paper.  If none are available, an empty list will be returned.
	 * @author Danielle
	 */
	public List<User> getAllUsers(final Paper the_paper, final int conf_id, final Role the_role)
	{
		List<User> user_pool;

		if(the_role == Role.REVIEWER)
		{	//remove the author, program chair, subprogram chair
			int author_id = the_paper.getAuthor().getID();

			user_pool = userDao.getUsers();

			User sp_chair = PaperService.getInstance().getAssignedSubprogramChair(the_paper.getID());
			int sp_chair_id = sp_chair.getID();

			List<User> pg_chairs = userDao.getUsers(conf_id, Role.PROGRAM_CHAIR);
			int pg_chair_id = 0;
			if(!pg_chairs.isEmpty())
			{
				pg_chair_id = pg_chairs.get(0).getID();
			}

			//Iterate through all users and remove ones which are not eligible.
			for(int i = 0; i< user_pool.size(); i++)
			{
				int current_user_id = user_pool.get(i).getID();
				if(current_user_id == author_id ||
						current_user_id == pg_chair_id	||
						current_user_id == sp_chair_id)
				{//Remove paper's author, pg chair and sub program chair
					user_pool.remove(i);
					i = i - 1;
				}
			}
		}
		else if(the_role == Role.SUB_PROGRAM_CHAIR)
		{//remove the author, remove anyone who is not a reviewer
			//get every reviewer for the conference
			user_pool = userDao.getUsers(Role.REVIEWER);
			for(int i = 0; i < user_pool.size(); i++)
			{
				if(user_pool.get(i).getID() == the_paper.getAuthor().getID())
				{
					user_pool.remove(i);
					break;
				}
			}
		}
		else
		{  //Program chair, User, author can be anyone
			user_pool = getAllUsers();
		}
		return user_pool;
	}

	/**
	 * Return an instance of this class statically.
	 * @return an instance of UserService
	 */
	public static UserService getInstance() {
		return new UserService();
	}
}
