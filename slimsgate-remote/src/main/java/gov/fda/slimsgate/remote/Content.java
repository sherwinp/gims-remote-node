package gov.fda.slimsgate.remote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Content")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cntn_pk;
    
    private String cntn_id;  
    
    private int cntn_status;

	
	public int getCntn_pk() {
		return cntn_pk;
	}

	public void setCntn_pk(int cntn_pk) {
		this.cntn_pk = cntn_pk;
	}

	public String getCntn_id() {
		return cntn_id;
	}

	public void setCntn_id(String cntn_id) {
		this.cntn_id = cntn_id;
	}

	public int getCntn_status() {
		return cntn_status;
	}

	public void setCntn_status(int cntn_status) {
		this.cntn_status = cntn_status;
	}

}
