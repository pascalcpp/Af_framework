package af.sql;

import java.sql.ResultSetMetaData;
import java.util.List;

public class AfSqlMeta
{
	public int index;
	public String label;
	public int type; // java.sql.Types
	public String typeName;
	
	// 读取每一列的 MetaData
	public static AfSqlMeta[] read(ResultSetMetaData rsmd) throws Exception
	{
		int numColumn = rsmd.getColumnCount();
		AfSqlMeta[] result = new AfSqlMeta[numColumn];		
		for(int i=0; i<numColumn ; i++)
		{
			AfSqlMeta m = result[i] = new AfSqlMeta();
			
			int column = i + 1;
			m.index = column;
			m.label = rsmd.getColumnLabel( column);
			m.type = rsmd.getColumnType( column );
			m.typeName = rsmd.getColumnName(column);
		}
		
		return result;		
	}
}
