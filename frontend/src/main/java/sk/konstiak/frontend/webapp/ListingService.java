package sk.konstiak.frontend.webapp;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import sk.konstiak.model.Advertisement;

import java.util.function.Consumer;

@Service
public class ListingService {

    private final TaskExecutor taskExecutor;

    public ListingService(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void retrieveAdvertisements(Flux<Advertisement> advertisementFlux,
                                       Consumer<Advertisement> newAdvertisementFound,
                                       Runnable searchingFinished) {
        taskExecutor.execute(() -> {
            advertisementFlux.subscribe(newAdvertisementFound);
            searchingFinished.run();
        });
    }
}
