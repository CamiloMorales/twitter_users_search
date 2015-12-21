package models;

import javax.persistence.*;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@Entity
public class Speaker extends Model
{
	@Id
	public Long id;
	
	@Required
	public String name;
	
	@Required
	@Email
	public String email;
	
	@Required
	@MinLength(value=10)
	@MaxLength(value=100)
	@Column(length = 1000)
	public String bio;
	
	@Required
	public String twitterId;
	
	@Required
	public String pictureUrl;
}
