package com.dev.srijan.main;


import com.dev.srijan.app.CredentialsFormat;
import com.dev.srijan.app.UserFormat;

import java.io.IOException;

import com.dev.srijan.app.Authenticate;

public class RunProgram {
	public static void main(String[] args) throws IOException {
        Authenticate newReservation = new Authenticate();
        //newReservation.runProgram();
		newReservation.init_func();
		newReservation.welcome_Message();
		newReservation.sign_In_Page();
    }

}
