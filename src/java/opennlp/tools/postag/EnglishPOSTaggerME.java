///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2002 Jason Baldridge and Gann Bierner
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package opennlp.tools.postag;

import java.io.*;
import java.util.zip.*;
import opennlp.common.english.*;
import opennlp.maxent.*;
import opennlp.maxent.io.*;

/**
 * A part of speech tagger that uses a model trained on English data from the
 * Wall Street Journal and the Brown corpus.  The latest model created
 * achieved >96% accuracy on unseen data.
 *
 * @author      Gann Bierner
 * @version     $Revision: 1.2 $, $Date: 2003/11/22 19:45:14 $
 */

public class EnglishPOSTaggerME extends POSTaggerME {

  public EnglishPOSTaggerME(String modelFile) {
    super(getModel(modelFile), new POSContextGenerator(new BasicEnglishAffixes()));
    _useClosedClassTagsFilter = true;
    _closedClassTagsFilter = new EnglishClosedClassTags();
  }

  private static MaxentModel getModel(String name) {
    try {
      return new SuffixSensitiveGISModelReader(new File(name)).getModel();
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * <p>Part-of-speech tag a string passed in on the command line. For
   * example: 
   *
   * <p>java opennlp.tools.postag.EnglishPOSTaggerME -test "Mr. Smith gave a car to his son on Friday."
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: EnglishPOSTTaggerME model < sentences");
      System.err.println("       EnglishPOSTTaggerME -test model \"sentence\"");
      System.exit(1);
    }
    int ai=0;
    boolean test = false;
    if (args[ai].equals("-test")) {
      ai++;
      test=true;
    }
    POSTaggerME tagger = new EnglishPOSTaggerME(args[ai++]);
    if (test) {
      System.out.println(tagger.tag(args[ai]));
    }
    else {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      for (String line = in.readLine(); line != null; line = in.readLine()) {
        System.out.println(tagger.tag(line));
      }
    }
  }
}