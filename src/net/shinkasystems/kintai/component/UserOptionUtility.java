package net.shinkasystems.kintai.component;

import java.util.ArrayList;
import java.util.List;

import net.shinkasystems.kintai.KintaiDB;
import net.shinkasystems.kintai.entity.User;
import net.shinkasystems.kintai.entity.UserDao;
import net.shinkasystems.kintai.entity.UserDaoImpl;
import net.shinkasystems.kintai.entity.sub.UserData;

import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.tx.LocalTransaction;

/**
 * 
 * @author Aogiri
 *
 */
public class UserOptionUtility {

	/**
	 * インスタンスの生成は制限されています。
	 */
	private UserOptionUtility() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	public static List<UserOption> getUserOptions() {
		
		List<UserOption> userOptions = new ArrayList<UserOption>();
		
		LocalTransaction transaction = KintaiDB.getLocalTransaction();
		try {
			transaction.begin();

			final UserDao dao = new UserDaoImpl();

			for (UserData userData: dao.selectUserData(SelectOptions.get())) {
				userOptions.add(new UserOption(userData.getId(), userData.getDisplayName()));
			}

		} finally {
			transaction.rollback();
		}
		

		
		return userOptions;
	}

	/**
	 * 指定のユーザーが決裁者となっているユーザーのリストを返します。
	 * 自分自身が決裁者である場合は、ユーザーのリストに自分自身は含みません。
	 * 
	 * @param authorityId
	 * @return
	 */
	public static List<UserOption> getUserOptions(int authorityId) {
		
		final List<UserOption> applicantOptions = new ArrayList<UserOption>();

		LocalTransaction transaction = KintaiDB.getLocalTransaction();
		try {
			transaction.begin();

			final UserDao dao = new UserDaoImpl();
			
			for (User user : dao.selectByAuthorityId(authorityId)) {
				if (user.getId() != authorityId) {
					applicantOptions.add(new UserOption(user.getId(), user.getDisplayName()));
				}
			}

		} finally {
			transaction.rollback();
		}
		
		return applicantOptions;
	}
}
