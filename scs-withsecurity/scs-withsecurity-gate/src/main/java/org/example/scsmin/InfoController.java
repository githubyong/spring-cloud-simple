package org.example.scsmin;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    EurekaClient eurekaClient;

    @GetMapping("/apps")
    public Applications getSvcs(){
        return eurekaClient.getApplications();
    }
}
