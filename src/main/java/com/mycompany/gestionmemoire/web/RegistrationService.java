package com.mycompany.gestionmemoire.web;

import com.mycompany.entities.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class RegistrationService {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public boolean enregistrerEtudiant(String nom, String email, String motDePasse) {
        String adresseNormalisee = email.trim().toLowerCase(Locale.ROOT);
        List<Users> comptesExistants = em.createQuery(
                "SELECT u FROM Users u WHERE LOWER(u.email) = :email", Users.class)
                .setParameter("email", adresseNormalisee)
                .setMaxResults(1)
                .getResultList();
        if (!comptesExistants.isEmpty()) {
            return false;
        }
        String empreinte = BCrypt.hashpw(motDePasse, BCrypt.gensalt(12));
        Date maintenant = new Date();
        Users user = new Users();
        user.setFullName(nom.trim());
        user.setEmail(adresseNormalisee);
        user.setPasswordHash(empreinte);
        user.setRole(SessionUtil.STUDENT);
        user.setActive(true);
        user.setFailedLoginCount(0);
        user.setCreatedAt(maintenant);
        user.setUpdatedAt(maintenant);
        em.persist(user);
        return true;
    }

    public static boolean motDePasseValide(String motDePasse) {
        if (motDePasse == null || motDePasse.length() < 8) {
            return false;
        }
        return motDePasse.getBytes(StandardCharsets.UTF_8).length <= 72;
    }
}
