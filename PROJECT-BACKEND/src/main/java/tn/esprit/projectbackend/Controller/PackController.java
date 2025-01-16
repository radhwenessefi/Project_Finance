package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Pack;
import tn.esprit.projectbackend.Service.IPackService;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/pack")
public class PackController {

    private IPackService iPackService;

    @PostMapping("/add")
    public ResponseEntity<Pack> addPack(@RequestBody Pack pack) {
        return ResponseEntity.ok(iPackService.addPack(pack));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable("id") int id) {
        iPackService.deletePack(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Pack> updatePack(@RequestBody Pack pack) {
        return ResponseEntity.ok(iPackService.updatePack(pack));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pack>> getAllPack() {
        return ResponseEntity.ok(iPackService.getAllPack());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pack> getPack(@PathVariable("id") int id) {
        return ResponseEntity.ok(iPackService.getPack(id));
    }

//    @PutMapping("/pack/affectToAccount")
//    public ResponseEntity<String> affecterPackToCompte(
//            @RequestParam("idAccount") String idAccountStr,
//            @RequestParam("idPack") String idPackStr,
//            @RequestParam("perte") Float perte) {
//        try {
//            Long idAccount = Long.parseLong(idAccountStr);
//            Long idPack = Long.parseLong(idPackStr);
//            iPackService.affecterPackToCompte(idAccount, idPack, perte);
//            return ResponseEntity.ok("Pack affecté avec succès");
//        } catch (NumberFormatException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID invalide");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erreur lors de l'affectation du pack au compte : " + e.getMessage());
//        }
//    }

//    @PostMapping("/demande")
//    public ResponseEntity<String> traiterDemandeAssurance(
//            @RequestParam("accountId") long accountId,
//            @RequestParam("packId") long packId) {
//        String result = iPackService.traiterDemandeAssurance(accountId, packId);
//        return ResponseEntity.ok(result);
//    }

}
