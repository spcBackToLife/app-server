package com.pk.email;

public class ADDRESSBOOKMail extends SenderWapper {

	/**
	 * If the mode is mail,it's an instance of {@link MailConfig}. If the mode
	 * is message,it's an instance of {@link MessageConfig}
	 */
	protected AppConfig config = null;
	public static final String ADDRESS = "address";
	public static final String TARGET = "target";

	public ADDRESSBOOKMail(AppConfig config) {
		this.config = config;
	}

	public void setAddress(String address, String name) {
		requestData.put(ADDRESS, name + "<" + address + ">");
	}

	public void setAddressbook(String target) {
		requestData.put(TARGET, target);
	}

	@Override
	public ISender getSender() {
		return new Mail(this.config);
	}

	public void subscribe() {
		getSender().subscribe(requestData);
	}

	public void unsubscribe() {
		getSender().unsubscribe(requestData);
	}
}
