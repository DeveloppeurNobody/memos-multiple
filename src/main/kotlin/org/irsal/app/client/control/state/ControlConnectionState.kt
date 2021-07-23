package org.irsal.app.client.control.state

enum class ControlConnectionState {
    NOT_CONNECTED,
    CONNECTED,
    NOT_LOGGED_USER,
    NOT_LOGGED_PASS,
    LOGGED_IN,
    WAIT_FOR_REPLY
}