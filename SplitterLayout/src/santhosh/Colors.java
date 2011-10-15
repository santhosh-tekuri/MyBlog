package santhosh;

import java.awt.Color;
/** Color class -- used to define a nice blue-ish background
 *
 * <p>Use this code at your own risk!  MageLang Institute is not
 * responsible for any damage caused directly or indirctly through
 * use of this code.
 * <p><p>
 * <b>SOFTWARE RIGHTS</b>
 * <p>
 * MageLang support classes, version 1.0, MageLang Institute
 * <p>
 * We reserve no legal rights to this code--it is fully in the
 * public domain. An individual or company may do whatever
 * they wish with source code distributed with it, including
 * including the incorporation of it into commerical software.
 *
 * <p>However, this code cannot be sold as a standalone product.
 * <p>
 * We encourage users to develop software with this code. However,
 * we do ask that credit is given to us for developing it
 * By "credit", we mean that if you use these components or
 * incorporate any source code into one of your programs
 * (commercial product, research project, or otherwise) that
 * you acknowledge this fact somewhere in the documentation,
 * research report, etc... If you like these components and have
 * developed a nice tool with the output, please mention that
 * you developed it using these components. In addition, we ask that
 * the headers remain intact in our source code. As long as these
 * guidelines are kept, we expect to continue enhancing this
 * system and expect to make other tools available as they are
 * completed.
 * <p>
 * The MageLang Support Classes Gang:
 * @version MageLang Support Classes 1.0, MageLang Insitute, 1997
 * @author <a href="http:www.scruz.net/~thetick">Scott Stanchfield</a>, <a href=http://www.MageLang.com>MageLang Institute</a>
 */
public class Colors {
	public static Color lightSkyBlue3 = new Color(141, 182, 205);

	public static String getJavaInitializationString(Color c) {
		if (c.equals(Color.black))
			return "java.awt.Color.black";
		else if (c.equals(Color.blue))
			return "java.awt.Color.blue";
		else if (c.equals(Color.cyan))
			return "java.awt.Color.cyan";
		else if (c.equals(Color.darkGray))
			return "java.awt.Color.darkGray";
		else if (c.equals(Color.gray))
			return "java.awt.Color.gray";
		else if (c.equals(Color.green))
			return "java.awt.Color.green";
		else if (c.equals(Color.lightGray))
			return "java.awt.Color.lightGray";
		else if (c.equals(Color.magenta))
			return "java.awt.Color.magenta";
		else if (c.equals(Color.orange))
			return "java.awt.Color.orange";
		else if (c.equals(Color.pink))
			return "java.awt.Color.pink";
		else if (c.equals(Color.red))
			return "java.awt.Color.red";
		else if (c.equals(Color.white))
			return "java.awt.Color.white";
		else if (c.equals(Color.yellow))
			return "java.awt.Color.yellow";
		else
			return "new java.awt.Color("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
		}
}