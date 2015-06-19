package adalberto.com.phunwaretest.interfaces;

public interface TaskListener<T> {
    void onTaskSuccess(T result);
    void onTaskFailure(Exception e);
}