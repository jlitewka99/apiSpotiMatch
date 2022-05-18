package tk.spotimatch.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.spotimatch.api.model.pairing.Pair;
import tk.spotimatch.api.service.PairService;

@RestController
public class PairController {

    @Autowired
    PairService pairService;

    @GetMapping("/pairs/{id}")
    public ResponseEntity<?> getPairById(@PathVariable Long id) {
        return ResponseEntity.of(pairService.findById(id));
    }

    @PostMapping("/pairs")
    public ResponseEntity<?> createPair(@RequestBody Pair pair) {
        return ResponseEntity.ok(pairService.save(pair));
    }

}
