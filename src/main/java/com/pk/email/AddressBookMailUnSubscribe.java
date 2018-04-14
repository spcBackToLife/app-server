package com.pk.email;

public class AddressBookMailUnSubscribe {

	public static void main(String[] args) {

		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Mail);
		ADDRESSBOOKMail addressbook = new ADDRESSBOOKMail(config);
		addressbook.setAddress("leo@apple.cn", "leo");
		addressbook.unsubscribe();
	}
}
