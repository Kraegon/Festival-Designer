package simulator;

/**
 * @author Lesley
 * For 2D-Designer
 * Use this class for checking timevalues and such.
 */

public class CheckInputs 
{
    public boolean isHourValue(String s)
    {
        if (Integer.parseInt(s) <= 24 && Integer.parseInt(s) >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isMinuteSecondValue(String s)
    {
        if (Integer.parseInt(s) <= 60 && Integer.parseInt(s) >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isDay(String s)
    {
        if (Integer.parseInt(s) <= 31 && Integer.parseInt(s) >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isMonth(String s)
    {
        if (Integer.parseInt(s) <= 12 && Integer.parseInt(s) >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isYear(String s)
    {
        if (Integer.parseInt(s) <= 9999 && Integer.parseInt(s) >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean onlyNumbers(String s)
    {
        for(char c : s.toCharArray()) 
        {
            if (!Character.isDigit(c))
            {
                return false;
            }
        }
        return true;
    }
    
    // Also returns false if the given string doenst contain any characters
    public boolean isFirstCharZero(String s)
    {
        if (s.length() < 1)
        {
            return false;
        }
        else
        {
            // 12:(00) is een uitzondering bij het detecteren van leading zero's
            if (s.length() > 1 && s.charAt(0) == '0' && s.charAt(1) != '0')
            {
                return true;
            }
            return false;
        }
    }
    
    public boolean checkString(String s)
    {
        if (s.trim().isEmpty() || s.length() > 20)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean checkNumber(String s)
    {
        if (!onlyNumbers(s))
        {
            return false;
        }
        else
        {
            if (s.trim().isEmpty())
            {
                return false;
            }
            else
            {
                if (Integer.parseInt(s) > 9999 || Integer.parseInt(s) < 1)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
    }
}
