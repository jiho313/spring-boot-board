package kr.co.jhta.vo;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
	
	private int no;
	private String email;
	private String encryptedPassword;
	private Date updateDate;
	private Date createDate;
	
	@Builder
	public User(int no, String email, String encryptedPassword, Date updateDate, Date createDate) {
		super();
		this.no = no;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

}
