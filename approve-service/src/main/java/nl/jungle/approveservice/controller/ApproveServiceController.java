package nl.jungle.approveservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jungle.approveservice.service.ApproveService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/approve")
@AllArgsConstructor
public class ApproveServiceController {

    private final ApproveService approveService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getApprove(final @PathVariable("id") String id) {
        log.info("The contact with id '{}' will be requested to be definitive", id);

        this.approveService.approve(id);

        return ResponseEntity.ok(format("The contact with id '%s' has been requested to be definitive", id));
    }

}
