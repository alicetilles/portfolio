package com.neu.prattle.exceptions;

import com.neu.prattle.service.IUserService;

/***
 * An representation of an error which is thrown where a request has been made for creation of a
 * user object that already exists in the system. Refer {@link com.neu.prattle.model.User#equals}
 * Refer {@link IUserService#addUser(com.neu.prattle.model.User)}
 *
 * @author CS5500 Fall 2019 Teaching staff, Team 9
 * @version dated 2019-10-06
 */
public class UserAlreadyPresentException extends RuntimeException {
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = -4845176561270017896L;

    /**
     * User is already present.
     *
     * @param message the message being supplied.
     */
    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
