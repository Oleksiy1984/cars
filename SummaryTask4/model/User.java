package ua.nure.orlovskyi.SummaryTask4.model;


public class User {
		private Integer id;
		private String login;
		private String password;
		private Role role;
		private String name;
		private String email;
		private Gender gender;
		private String mobile;
		private String passport;
		private Boolean isBlocked;
		
		
		public String getPassport() {
			return passport;
		}
		public void setPassport(String passport) {
			this.passport = passport;
		}
		public Boolean getIsBlocked() {
			return isBlocked;
		}
		public void setIsBlocked(Boolean isBlocked) {
			this.isBlocked = isBlocked;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getLogin() {
			return login;
		}
		public void setLogin(String login) {
			this.login = login;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Gender getGender() {
			return gender;
		}
		public void setGender(Gender gender) {
			this.gender = gender;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
		@Override
		public String toString() {
			return "User [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + ", name="
					+ name + ", email=" + email + ", gender=" + gender + ", mobile=" + mobile + ", passport=" + passport
					+ ", isBlocked=" + isBlocked + "]";
		}

	
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + ((gender == null) ? 0 : gender.hashCode());
			result = prime * result + ((isBlocked == null) ? 0 : isBlocked.hashCode());
			result = prime * result + ((login == null) ? 0 : login.hashCode());
			result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((passport == null) ? 0 : passport.hashCode());
			result = prime * result + ((password == null) ? 0 : password.hashCode());
			result = prime * result + ((role == null) ? 0 : role.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (gender != other.gender)
				return false;
			if (isBlocked == null) {
				if (other.isBlocked != null)
					return false;
			} else if (!isBlocked.equals(other.isBlocked))
				return false;
			if (login == null) {
				if (other.login != null)
					return false;
			} else if (!login.equals(other.login))
				return false;
			if (mobile == null) {
				if (other.mobile != null)
					return false;
			} else if (!mobile.equals(other.mobile))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (passport == null) {
				if (other.passport != null)
					return false;
			} else if (!passport.equals(other.passport))
				return false;
			if (password == null) {
				if (other.password != null)
					return false;
			} else if (!password.equals(other.password))
				return false;
			if (role != other.role)
				return false;
			return true;
		}
		
		
		
		
}
