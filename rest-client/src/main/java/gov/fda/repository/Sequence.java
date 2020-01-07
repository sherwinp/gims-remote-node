package gov.fda.repository;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name="Content")
public class Sequence implements Serializable {
	//cntn_fk_location, cntn_id, cntn_barCode, cntn_fk_contentType,cntn_fk_status, cntn_createdBy, cntn_modifiedBy, cntn_modifiedOn
	//auto set by db
    private int cntn_pk;
	//sandbox
    private int cntn_fk_location=5;
	@Id    
    private String cntn_id;  
	//pending
    private int cntn_status = 1028;
    private String  cntn_barCode;
    private int cntn_fk_contentType;
    private String cntn_createdBy; 
    private String cntn_modifiedBy;
    private Date cntn_modifiedOn;
    
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
    public int getCntn_fk_location() {
		return cntn_fk_location;
	}

	public void setCntn_fk_location(int cntn_fk_location) {
		this.cntn_fk_location = cntn_fk_location;
	}

	public String getCntn_barCode() {
		return cntn_barCode;
	}

	public void setCntn_barCode(String cntn_barCode) {
		this.cntn_barCode = cntn_barCode;
	}

	public int getCntn_fk_contentType() {
		return cntn_fk_contentType;
	}

	public void setCntn_fk_contentType(int cntn_fk_contentType) {
		this.cntn_fk_contentType = cntn_fk_contentType;
	}

	public String getCntn_createdBy() {
		return cntn_createdBy;
	}

	public void setCntn_createdBy(String cntn_createdBy) {
		this.cntn_createdBy = cntn_createdBy;
	}

	public String getCntn_modifiedBy() {
		return cntn_modifiedBy;
	}

	public void setCntn_modifiedBy(String cntn_modifiedBy) {
		this.cntn_modifiedBy = cntn_modifiedBy;
	}

	public Date getCntn_modifiedOn() {
		return cntn_modifiedOn;
	}

	public void setCntn_modifiedOn(Date cntn_modifiedOn) {
		this.cntn_modifiedOn = cntn_modifiedOn;
	}

	public int getCntn_status() {
		return cntn_status;
	}

	public void setCntn_status(int cntn_status) {
		this.cntn_status = cntn_status;
	}
	@Transient
	static Content createNew(EntityManager em) {
		Content cntn = new Content();
		Query query = em.createQuery("select top 1  cntn_barCode from content where cntn_barCode like 'SMP%' order by cntn_pk desc");
		Query queryType = em.createQuery("select top 1  cntn_pk from contenttype where type='Sequence'");
		Object o = query.getSingleResult();
		Integer num = Integer.parseInt(String.format("%s", o).substring(3)) + 1;
		cntn.setCntn_barCode(String.format("SEQ%########d", num));
		cntn.setCntn_id(cntn.getCntn_barCode());
		cntn.setCntn_createdBy(System.getenv("user"));
		cntn.setCntn_fk_contentType((Integer)queryType.getSingleResult());
		/*
		 * SELECT *  FROM content
where cntn_barCode = 'SMP000097463'

INSERT INTO GIMSDevDB_54.dbo.content
(cntn_fk_location, cntn_id, cntn_barCode, cntn_fk_contentType,cntn_fk_status, cntn_createdBy, cntn_modifiedBy, cntn_modifiedOn)
VALUES(5, 'SMP000097463', 'SMP000097463', 5, 1028, 'SlimsGate','SlimsGate', getdate());
		 * */
		return null;
		
	}

}
