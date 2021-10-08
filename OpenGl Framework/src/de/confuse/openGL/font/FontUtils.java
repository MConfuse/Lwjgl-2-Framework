package de.confuse.openGL.font;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontUtils
{
	public static final FontUtils ARIAL = new FontUtils("C:/Windows/Fonts/Arial.ttf", Font.TRUETYPE_FONT, 128);
	public static final FontUtils ARIAL_HALF = new FontUtils("C:/Windows/Fonts/Arial.ttf", Font.TRUETYPE_FONT, 64);

	private final UnicodeFont unicodeFont;
	private final int[] colorCodes = new int[32];

	@SuppressWarnings("unchecked")
	public FontUtils(String fontName, int fontType, int size)
	{
		this.unicodeFont = new UnicodeFont(new Font(fontName, Font.TRUETYPE_FONT, size));

		try
		{
			this.unicodeFont.addAsciiGlyphs();
			this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
			this.unicodeFont.loadGlyphs();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < 32; i++)
		{
			int shadow = (i >> 3 & 0x1) * 85;
			int red = (i >> 2 & 0x1) * 170 + shadow;
			int green = (i >> 1 & 0x1) * 170 + shadow;
			int blue = (i >> 0 & 0x1) * 170 + shadow;

			if (i == 6)
			{
				red += 85;
			}

			if (i >= 16)
			{
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
		}
	}

	public void drawString(String text, float x, double d, int color)
	{
		x *= 2.0F;
		d *= 2.0D;
		float originalX = x;

		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glScaled(0.5D, 0.5D, 0.5D);

		int currentColor = color;
		char[] characters = text.toCharArray();

		int index = 0;
		byte b;
		int i;
		char[] arrayOfChar1;
		for (i = (arrayOfChar1 = characters).length, b = 0; b < i;)
		{
			char c = arrayOfChar1[b];
			if (c == '\r')
			{
				x = originalX;
			}
			if (c == '\n')
			{
				d += (getHeight(Character.toString(c)) * 2.0F);
			}
			if (c != '§' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '§'))
			{
				this.unicodeFont.drawString(x, (float) d, Character.toString(c), new Color(currentColor));
				x += getWidth(Character.toString(c)) * 2.0F;
			}
			else if (c == ' ')
			{
				x += this.unicodeFont.getSpaceWidth();
			}
			else if (c == '§' && index != characters.length - 1)
			{
				int codeIndex = "0123456789abcdef".indexOf(text.charAt(index + 1));
				if (codeIndex < 0)
					continue;
				int col = this.colorCodes[codeIndex];
				currentColor = col;
			}
			index++;
			b++;
			continue;
		}

		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public void drawStringWidthShadow(String text, float x, float y, int color)
	{
		drawString(text, x + 0.5F, y + 0.5D, 0);
		drawString(text, x, y, color);
	}

	public void drawCenteredString(String text, float x, float y, int color)
	{
		drawString(text, x / 2.0F - getWidth(text) / 2.0F, y, color);
	}

	public void drawCenteredStringWidthShadow(String text, float x, float y, int color)
	{
		drawString(text, x + 0.5F, y + 0.5D, color);
		drawString(text, x, y, color);
	}

	public float getWidth(String s)
	{
		float width = 0.0F;

		byte b;
		int i;
		char[] arrayOfChar;
		for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i;)
		{
			char c = arrayOfChar[b];
			width += this.unicodeFont.getWidth(Character.toString(c));
			b++;
		}

		return width / 2.0F;
	}

	public float getHeight(String s)
	{
		return this.unicodeFont.getHeight(s) / 2.0F;
	}

	public float getHeight()
	{ return this.unicodeFont.getLineHeight() / 2f; }

	public UnicodeFont getFont()
	{ return this.unicodeFont; }
}
