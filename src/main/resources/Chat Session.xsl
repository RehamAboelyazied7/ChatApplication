<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">

        <html>

            <head>
            </head>

            <body>

                <!--Header for chat room-->
                <h1>
                    <span class="head">Chat Room name </span> :
                    <xsl:value-of select="chatRoom/Name"></xsl:value-of>
                </h1>


                <!--List of users in a table-->
                <h1 class="head">
                    List of users
                </h1>

                <table>
                    <tr>
                        <th>Number</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                    </tr>

                    <xsl:for-each select="chatRoom/User">

                        <tr>
                            <td>
                                <xsl:value-of select="position()"/>
                            </td>
                            <td>
                                <xsl:value-of select="FirstName"> </xsl:value-of> &#160;
                                <xsl:value-of
                                        select="LastName"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="Email"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="Phone"></xsl:value-of>
                            </td>

                        </tr>

                    </xsl:for-each>
                </table>


                <!--Messages in table-->
                <h1 class="head">Messages in room</h1>

                <table>

                    <tr>
                        <th>Sender</th>
                        <th>Content</th>
                    </tr>

                    <xsl:for-each select="chatRoom/Message">

                        <tr>

                            <td>
                                <xsl:value-of select="Sender/FirstName"> </xsl:value-of> &#160;
                                <xsl:value-of select="Sender/LastName"></xsl:value-of>
                            </td>

                            <td>

                                <xsl:attribute name="style">

                                    font-weight :
                                    <xsl:value-of select="Style/FontWeight"></xsl:value-of>;

                                    color :
                                    <xsl:value-of select="Style/Color"></xsl:value-of>;

                                    font-style :
                                    <xsl:value-of select="Style/FontPosture"></xsl:value-of>;

                                    font-size :
                                    <xsl:value-of select="Style/Size"></xsl:value-of>;

                                    font-family :
                                    <xsl:value-of select="Style/FontFamily"></xsl:value-of>;

                                </xsl:attribute>

                                <xsl:value-of select="Content"></xsl:value-of>

                            </td>

                        </tr>

                    </xsl:for-each>

                </table>

                <style>
                    th{

                        background: #4c84ff;
                        padding: 10px 10px 10px 10px;

                    }

                    td{

                        padding: 10px 10px 10px 10px;

                    }

                    table{

                        background-color: #ebebeb;
                        border-radius: 5px;

                    }

                    .head{

                        text-decoration: underline;
                        color: #4c84ff;

                    }

                </style>

            </body>

        </html>

    </xsl:template>

</xsl:stylesheet>
