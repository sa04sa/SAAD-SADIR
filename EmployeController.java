package Controller;

import Model.EmployeModel;
import Model.Poste;
import Model.Role;
import View.EmployeView;

public class EmployeController {
    private EmployeView view;
    private EmployeModel model;

    public EmployeController(EmployeView view, EmployeModel model) {
        this.view = view;
        this.model = model;

        this.view.getAjouterButton().addActionListener(e -> add());
        this.view.getModifierButton().addActionListener(e -> update());
        this.view.getSupprimerButton().addActionListener(e -> delete());
        this.view.getAfficherButton().addActionListener(e -> employes());
    }

    // Méthode pour ajouter un employé
    private void add() {
        try {
            String nom = view.getNom().trim();
            String prenom = view.getPrenom().trim();
            String email = view.getEmail().trim();
            String telephone = view.getTelephone().trim();
            Double salaire = view.getSalaire();
            Role role = (Role) view.roleComboBox.getSelectedItem(); // Cast pour obtenir l'objet Role
            Poste poste = (Poste) view.posteComboBox.getSelectedItem();

            // Validation des champs obligatoires
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || role == null || poste == null) {
                view.afficherMessageErreur("Tous les champs obligatoires doivent être remplis.");
                return;
            }

            // Appel au modèle pour ajouter l'employé
            boolean ajoutReussi = model.add( nom, prenom, email, telephone, salaire, role, poste);
            if (ajoutReussi) {
                view.afficherMessageSucces("Employé ajouté avec succès.");
                employes(); // Actualiser la liste des employés
            } else {
                view.afficherMessageErreur("Échec de l'ajout de l'employé.");
            }
        } catch (NumberFormatException e) {
            view.afficherMessageErreur("Le salaire doit être un nombre valide.");
        } catch (Exception e) {
            view.afficherMessageErreur("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un employé
    private void update() {
        try {
            int selectedRow = view.table.getSelectedRow();
            if (selectedRow == -1) {
                view.afficherMessageErreur("Veuillez sélectionner un employé à modifier.");
                return;
            }

            // Récupération des données depuis la vue
            String nom = view.getNom().trim();
            String prenom = view.getPrenom().trim();
            String email = view.getEmail().trim();
            String telephone = view.getTelephone().trim();
            Double salaire = view.getSalaire();
            Role role = view.getRole();
            Poste poste = view.getPoste();

            // Validation des champs obligatoires
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || role == null || poste == null) {
                view.afficherMessageErreur("Tous les champs obligatoires doivent être remplis.");
                return;
            }

            // Récupération de l'ID de l'employé depuis la table
            int id = (int) view.model.getValueAt(selectedRow, 0);

            // Appel au modèle pour mettre à jour l'employé
            boolean miseAJourReussie = model.update(id, nom, prenom, email, telephone, salaire, role, poste);
            if (miseAJourReussie) {
                view.afficherMessageSucces("Employé modifié avec succès.");
                employes(); // Actualiser la liste des employés
            } else {
                view.afficherMessageErreur("Échec de la modification de l'employé.");
            }
        } catch (NumberFormatException e) {
            view.afficherMessageErreur("Le salaire doit être un nombre valide.");
        } catch (Exception e) {
            view.afficherMessageErreur("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }

    // Méthode pour supprimer un employé
    private void delete() {
        try {
            int selectedRow = view.table.getSelectedRow();
            if (selectedRow == -1) {
                view.afficherMessageErreur("Veuillez sélectionner un employé à supprimer.");
                return;
            }

            // Récupération de l'ID de l'employé depuis la table
            int id = (int) view.model.getValueAt(selectedRow, 0);

            // Appel au modèle pour supprimer l'employé
            boolean suppressionReussie = model.delete(id);
            if (suppressionReussie) {
                view.afficherMessageSucces("Employé supprimé avec succès.");
                employes(); // Actualiser la liste des employés
            } else {
                view.afficherMessageErreur("Échec de la suppression de l'employé.");
            }
        } catch (Exception e) {
            view.afficherMessageErreur("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les employés
    private void employes() {
        try {
            Object[][] employes = model.employes();
            view.model.setRowCount(0); // Vider la table avant d'ajouter les données
            for (Object[] emp : employes) {
                view.model.addRow(emp);

            }
            System.out.println("bien affiche");
        } catch (Exception e) {
            view.afficherMessageErreur("Une erreur est survenue lors du chargement des employés : " + e.getMessage());
        }
    }
}
