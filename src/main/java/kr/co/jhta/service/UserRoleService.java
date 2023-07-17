package kr.co.jhta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.UserRoleDao;

@Service
public class UserRoleService {
	
	@Autowired
	private UserRoleDao userRoleDao;
	
}
