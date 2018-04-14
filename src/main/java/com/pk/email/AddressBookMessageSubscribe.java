package com.pk.email;

public class AddressBookMessageSubscribe {

	public static void main(String[] args) {

		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		ADDRESSBOOKMessage addressbook = new ADDRESSBOOKMessage(config);
		// addressbook.setAddress("186****7150");
		addressbook.subscribe();
	}
}
