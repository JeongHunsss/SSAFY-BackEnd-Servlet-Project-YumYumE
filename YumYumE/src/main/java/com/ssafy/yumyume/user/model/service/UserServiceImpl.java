package com.ssafy.yumyume.user.model.service;

import java.sql.SQLException;

import com.ssafy.yumyume.user.model.dao.UserDao;
import com.ssafy.yumyume.user.model.dao.UserDaoImpl;
import com.ssafy.yumyume.user.model.dto.UserDto;

public class UserServiceImpl implements UserService {

	private UserDao dao = UserDaoImpl.getDao();
	
	private static UserService service = new UserServiceImpl();
	private UserServiceImpl() {}
	public static UserService getService()	{
		return service;
	}
	@Override
	public void registUser(UserDto user) throws SQLException {
		if(idDuplicate(user.getId())) {
			throw new SQLException("이미 존재하는 아이디입니다.");
		}
		dao.registUser(user);
	}
	@Override
	public boolean idDuplicate(String id) throws SQLException {
//		if(dao.idDuplicate(id)) {
//			System.out.println("중복222!!!!!");
//			throw new SQLException("이미 존재하는 아이디입니다.");
//		}
		return dao.idDuplicate(id);
	}
	@Override
	public UserDto login(String id, String password) throws SQLException {
		return dao.login(id, password);
	}
	@Override
	public UserDto getUserInfobyId(String id) throws SQLException {
		return dao.getUserInfobyId(id);
	}
	@Override
	public void updateUser(UserDto user) throws SQLException {
		dao.updateUser(user);
	}
	@Override
	public void deleteUser(String id) throws SQLException {
		dao.deleteUser(id);
		
	}
	


}
