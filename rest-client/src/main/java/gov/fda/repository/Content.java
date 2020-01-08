package gov.fda.repository;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
@Table(name = "content")
public class Content implements Serializable {
	public Content() {

	}

	// cntn_fk_location, cntn_id, cntn_barCode, cntn_fk_contentType,cntn_fk_status,
	// cntn_createdBy, cntn_modifiedBy, cntn_modifiedOn
	// auto set by db
	private int cntn_pk;
	// sandbox
	private int cntn_fk_location = 5;
	@Id
	private String cntn_id;
	// pending
	private int cntn_status = 10;
	private String cntn_barCode;
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
	public void store(EntityManager em, JsonParser parser) {
		Query query = em.createNativeQuery("select max(cn.cntn_id), ct.cntp_pk from content cn \n"
				+ "right outer join contenttype ct on cn.cntn_fk_contentType =ct.cntp_pk "
				+ " and ct.cntp_name like '%sample%'  group by ct.cntp_pk");
		List<Object[]> result = query.getResultList();
		Integer num = 1;
		if (!result.isEmpty()) {
			if (result.get(0) != null && result.get(0).length > 0 && result.get(0)[0] != null) {
				num = Integer.parseInt(String.format("%s", result.get(0)[0]).substring(3)) + 1;
			}
			// always a contenttype definition
			setCntn_fk_contentType((Integer) result.get(0)[1]);
		}
		setCntn_barCode(String.format("SMP%08d", num));
		setCntn_id(getCntn_barCode());
		setCntn_createdBy(System.getenv("user"));
		setCntn_modifiedOn(new java.sql.Date(new java.util.Date().getTime()));
		em.persist(this);
		/*
		 * SELECT * FROM content where cntn_barCode = 'SMP000097463'
		 * 
		 * INSERT INTO GIMSDevDB_54.dbo.content (cntn_fk_location, cntn_id,
		 * cntn_barCode, cntn_fk_contentType,cntn_fk_status, cntn_createdBy,
		 * cntn_modifiedBy, cntn_modifiedOn) VALUES(5, 'SMP000097463', 'SMP000097463',
		 * 5, 1028, 'SlimsGate','SlimsGate', getdate());
		 */
	}

}
