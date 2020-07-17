package my.db; 

import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2019-08-31 14:36:15
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `topic`
        (`id`, `title`, `userId`, `content`, `numView`, `numReply`, `flagTop`, `flagNice`, `timeCreated`, `timeMofified`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `topic`
        (`id`, `title`, `userId`, `content`, `numView`, `numReply`, `flagTop`, `flagNice`, `timeCreated`, `timeMofified`) 
  VALUES(#{id}, #{title}, #{userId}, #{content}, #{numView}, #{numReply}, #{flagTop}, #{flagNice}, #{timeCreated}, #{timeMofified}) 

  自增主键: id
*/ 

public class Topic 
{ 
 
	public Long id ; 
	public String title ; 
	public Integer userId ; 
	public String content ; 
	public Integer numView ; 
	public Integer numReply ; 
	public Byte flagTop ; 
	public Byte flagNice ; 
	public Date timeCreated ; 
	public Date timeMofified ; 


	public void setId(Long id)
	{
		this.id=id;
	}
	public Long getId()
	{
		return this.id;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getTitle()
	{
		return this.title;
	}
	public void setUserId(Integer userId)
	{
		this.userId=userId;
	}
	public Integer getUserId()
	{
		return this.userId;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String getContent()
	{
		return this.content;
	}
	public void setNumView(Integer numView)
	{
		this.numView=numView;
	}
	public Integer getNumView()
	{
		return this.numView;
	}
	public void setNumReply(Integer numReply)
	{
		this.numReply=numReply;
	}
	public Integer getNumReply()
	{
		return this.numReply;
	}
	public void setFlagTop(Byte flagTop)
	{
		this.flagTop=flagTop;
	}
	public Byte getFlagTop()
	{
		return this.flagTop;
	}
	public void setFlagNice(Byte flagNice)
	{
		this.flagNice=flagNice;
	}
	public Byte getFlagNice()
	{
		return this.flagNice;
	}
	public void setTimeCreated(Date timeCreated)
	{
		this.timeCreated=timeCreated;
	}
	public Date getTimeCreated()
	{
		return this.timeCreated;
	}
	public void setTimeMofified(Date timeMofified)
	{
		this.timeMofified=timeMofified;
	}
	public Date getTimeMofified()
	{
		return this.timeMofified;
	}

} 
 