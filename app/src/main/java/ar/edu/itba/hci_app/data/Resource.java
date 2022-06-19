package ar.edu.itba.hci_app.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final ar.edu.itba.hci_app.model.Error error;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable ar.edu.itba.hci_app.model.Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(ar.edu.itba.hci_app.model.Error error, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }
}