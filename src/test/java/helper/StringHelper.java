package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class StringHelper {

    public static int countMatches(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);

        int count = 0;
        int pos = 0;
        while (matcher.find(pos))
        {
            count++;
            pos = matcher.start() + 1;
        }

        return count;
    }
}
