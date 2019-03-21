package cn.edu.sicau.exception;

public class UserException extends Exception {

	/**
	 *	自定义一个异常类
	 *		只需要继承构造方法即可，方便创建对象
	 */
	public UserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
