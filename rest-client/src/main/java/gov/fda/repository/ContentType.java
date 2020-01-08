package gov.fda.repository;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contenttype")
public class ContentType implements Serializable {
	@Id
	private int cntp_pk;
}