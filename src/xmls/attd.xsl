<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
<html>
<body>
    <table>
        <tr >
            <th>date</th>
            <th>hour</th>
            <th>rollno</th>
            <th>status</th>
        </tr>
        <xsl:for-each select="attd/attd_stat">
            <tr>
                <td>
                    <xsl:value-of select="date"/>
                </td>
                <td>
                    <xsl:value-of select="hour"/>
                </td>
                <td>
                    <xsl:value-of select="rollno"/>
                </td>
                <td>
                    <xsl:value-of select="status"/>
                </td>
            </tr>
        </xsl:for-each>
    </table> 
</body>
</html>
</xsl:template>
</xsl:stylesheet>