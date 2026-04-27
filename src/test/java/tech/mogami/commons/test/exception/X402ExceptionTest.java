package tech.mogami.commons.test.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.exception.InvalidX402PayloadException;
import tech.mogami.commons.exception.InvalidX402SchemeException;
import tech.mogami.commons.exception.InvalidX402VersionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("X402 exception tests")
public class X402ExceptionTest {

    @Test
    @DisplayName("InvalidX402HeaderException(message) should set message and no cause")
    void headerExceptionWithMessage() {
        InvalidX402HeaderException exception = new InvalidX402HeaderException("bad header");

        assertThat(exception.getMessage()).isEqualTo("bad header");
        assertThat(exception.getCause()).isNull();
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("InvalidX402HeaderException(message, cause) should set message and cause")
    void headerExceptionWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("root");
        InvalidX402HeaderException exception = new InvalidX402HeaderException("bad header", cause);

        assertThat(exception.getMessage()).isEqualTo("bad header");
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("InvalidX402PayloadException(message) should set message and no cause")
    void payloadExceptionWithMessage() {
        InvalidX402PayloadException exception = new InvalidX402PayloadException("bad payload");

        assertThat(exception.getMessage()).isEqualTo("bad payload");
        assertThat(exception.getCause()).isNull();
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("InvalidX402PayloadException(message, cause) should set message and cause")
    void payloadExceptionWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("root");
        InvalidX402PayloadException exception = new InvalidX402PayloadException("bad payload", cause);

        assertThat(exception.getMessage()).isEqualTo("bad payload");
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("InvalidX402SchemeException(message) should set message and no cause")
    void schemeExceptionWithMessage() {
        InvalidX402SchemeException exception = new InvalidX402SchemeException("bad scheme");

        assertThat(exception.getMessage()).isEqualTo("bad scheme");
        assertThat(exception.getCause()).isNull();
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("InvalidX402SchemeException(message, cause) should set message and cause")
    void schemeExceptionWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("root");
        InvalidX402SchemeException exception = new InvalidX402SchemeException("bad scheme", cause);

        assertThat(exception.getMessage()).isEqualTo("bad scheme");
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("InvalidX402VersionException(message) should set message and no cause")
    void versionExceptionWithMessage() {
        InvalidX402VersionException exception = new InvalidX402VersionException("bad version");

        assertThat(exception.getMessage()).isEqualTo("bad version");
        assertThat(exception.getCause()).isNull();
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("InvalidX402VersionException(message, cause) should set message and cause")
    void versionExceptionWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("root");
        InvalidX402VersionException exception = new InvalidX402VersionException("bad version", cause);

        assertThat(exception.getMessage()).isEqualTo("bad version");
        assertThat(exception.getCause()).isSameAs(cause);
    }

}
