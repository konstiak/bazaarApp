package sk.konstiak.frontend.frontendvaadin;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Service
public class ListingService<T> {

    private final TaskExecutor taskExecutor;

    public ListingService(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void retrieveItems(Flux<T> itemFlux,
                              Consumer<T> nextItem,
                              Runnable searchingFinished) {
        taskExecutor.execute(() -> {
            itemFlux.subscribe(nextItem);
            searchingFinished.run();
        });
    }
}
