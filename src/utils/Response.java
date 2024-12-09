package utils;

public class Response<T> {
	public boolean isSuccess;
	public String message;
	public T data;
	
	private Response(String message, T data, boolean isSuccess) {
		this.message = message;
		this.data = data;
		this.isSuccess = isSuccess;
	}
	
    public static <T> Response<T> success(String message, T data) {
        return new Response<>(message, data, true);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(message, null, false);
    }
}
