package models;

import javax.persistence.*;
//import javax.validation.Valid;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;

@Entity
public class Proposal extends Model
{
	@Id
	public Long id;
	
	@Required
	public String title;
	
	@Required
	@MinLength(value=10)
	@MaxLength(value=100)
	@Column(length = 1000)
	public String proposal;
	
	@Required
	public SessionType type = SessionType.OneHourTalk;
	
	@Required
	public Boolean isApproved = false;

	public String keywords;
	
	//@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public Speaker speaker;
}
