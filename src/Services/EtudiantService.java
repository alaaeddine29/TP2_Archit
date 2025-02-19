package Services;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class EtudiantService {
	private InterfaceEtudeRep StudRep;
    private InterfaceUnivRep UnivRep;
    private IJournal journal;
	public EtudiantService(InterfaceEtudeRep StudRep, InterfaceUnivRep UnivRep,IJournal journal) {
		this.StudRep = StudRep;
		this.UnivRep = UnivRep;
		this.journal = journal;
	}
	
	
	public boolean inscription (InterfaceEtudiant stud) throws SQLException, IOException	
	{
	    //Universite univ=UnivRep.GetById(stud.getId_universite());
	    
	    //System.out.println("Log: d�but de l'op�ration d'ajout de l'�tudiant avec matricule "+stud.getMatricule());
	    journal.outPut_Msg("Log: d�but de l'op�ration d'ajout de l'�tudiant avec matricule "+stud.getMatricule());
	    // la v�rification de mail et matricule devient dans la classe EtudiantRepository
	    if(StudRep.VerifEmailMat(stud)) {
	    	return false;
	    }
		
	 // la verfication de type package pour avoir le nombre de livre autoris�  devient dans la classe UniversiteRepository et ici en initialse le nombre de livre
		 int nbrlivreAutoris� = UnivRep.NbrLivreAutorise(stud.getId_universite());
		   stud.setNbLivreMensuel_Autorise(nbrlivreAutoris�);
		 
		 //ajouter le bonus 
		  // � tous les �tudiants avec forfait limit� standard et limit� premium
		   //donc dans l'inscription on ajout ce bonus
		 //AjouterBonus(stud);
		 StudRep.add(stud);
		 //System.out.println("Log: Fin de l'op�ration d'ajout de l'�tudiant avec matricule "+stud.getMatricule());
		 journal.outPut_Msg("Log: Fin de l'op�ration d'ajout de l'�tudiant avec matricule "+stud.getMatricule());
		 return true;
	    
		
	}
	/* j'ai modifi� la fonction ajouterBonus avec l'utlisation GetEtudiantParUniversitye 
	 * dedans cela permis de recuperer tous les etudiants 
	 * dans la fonction GetEtudiantParUniversitye j'ai consid�r� que cette fonction
	 *  est pr�d�finie et elle va nous retourner une liste des �tudiants 
	 *  donc on va les ajouter le bonus selon l'offre
	 *  et donc on peut appeler cette fonction AjouterBonus o� on veut  
	 * */
	public void AjouterBonus() throws SQLException, IOException {
		 	//ici on r�cupere le nobre de livre bonus selon le type de package 
			// et la v�rifcation ce faite dans la classe universiteRepository avec la methode NbrLivreBonus
			//int nbrlivreBonus = UnivRep.NbrLivreBonus(stud.getId_universite());
			// on envoie le nombre de livre envoy� par la fonction de bonus 
			ArrayList<Etudiant> etudliste =  GetEtudiantParUniversitye();
			for( Etudiant i:etudliste) {
				i.Bonus(UnivRep.NbrLivreBonus(i.getId_universite()));
			}
			
	}
	
	

public ArrayList<Etudiant> GetEtudiantParUniversitye()
{
    //...
	return new ArrayList<>(4);
}

public ArrayList<Etudiant> GetEtudiatparLivreEmprunte()
{
    //...
	return new ArrayList<>(4);
	
}


	
}
