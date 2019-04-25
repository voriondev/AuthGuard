package fr.vorion.authguard.credentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.warrenstrange.googleauth.ICredentialRepository;

import fr.vorion.authguard.Main;

public class Credentials implements ICredentialRepository
{

	public Main main;

	public Credentials(Main m)
	{
		main = m;
	}

	@Override
	public String getSecretKey(String username)
	{
		try
		{
			Statement statement = main.sqlConnection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM secured_accounts WHERE pseudo = '" + username + "';");
			while(res.next())
			{
				if(res.getString("pseudo") == null)
				{
					return "NullPointerExceptionOccured";
				}
				else
				{
					return res.getString("secret_key");
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "NullPointerExceptionOccured";
	}

	@Override
	public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes)
	{
		try
		{
			PreparedStatement ps = main.sqlConnection.prepareStatement("INSERT INTO `secured_accounts`(pseudo, secret_key, code1, code2) VALUES (?, ?, ?, ?);");
			ps.setString(1, userName);
			ps.setString(2, secretKey);
			ps.setString(3, scratchCodes.get(0).toString());
			ps.setString(4, scratchCodes.get(scratchCodes.size() - 1).toString());
			ps.executeUpdate();
		}
		catch(SQLException e)
		{

		}
	}

	public void saveAddress(String username, String address)
	{
		try
		{
			PreparedStatement ps = main.sqlConnection.prepareStatement("INSERT INTO `authorized_ips`(pseudo, ip, date) VALUES (?, ?, ?);");
			ps.setString(1, username);
			ps.setString(2, address);
			Date date = new Date();
			ps.setString(3, date.toString());
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public boolean checkAddress(String username, String ip)
	{
		try
		{
			Statement statement = main.sqlConnection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM authorized_ips WHERE pseudo= '" + username + "';");
			ArrayList<String> ips = new ArrayList<String>();
			boolean authorized = false;
			while(res.next())
			{
				if(res.getString("pseudo") == null)
				{
					return false;
				}
				else
				{
					ips.add(res.getString("ip"));
				}
			}
			return authorized = ips.contains(ip);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkEmergencyCode(String username, String code)
	{
		try
		{
			Statement statement = main.sqlConnection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM secured_accounts WHERE pseudo= '" + username + "';");
			ArrayList<String> codes = new ArrayList<String>();
			boolean isValid = false;
			while(res.next())
			{
				if(res.getString("pseudo") == null)
				{
					return false;
				}
				else
				{
					codes.add(res.getString("code1"));
					codes.add(res.getString("code2"));
				}
			}
			return isValid = codes.contains(code);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void removeUser(String username)
	{
		try
		{
			PreparedStatement ps = main.sqlConnection.prepareStatement("DELETE FROM secured_accounts WHERE pseudo = '" + username + "';");
			ps.executeUpdate();

			ps = main.sqlConnection.prepareStatement("DELETE FROM authorized_ips WHERE pseudo = '" + username + "';");
			ps.executeUpdate();
		}
		catch(SQLException e)
		{

		}
	}

}
