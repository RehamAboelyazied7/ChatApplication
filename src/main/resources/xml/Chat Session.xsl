<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">

        <html>

            <head>

                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <meta charset="UTF-8"/>
                <title><xsl:value-of select="chatRoom/Name"></xsl:value-of> chat room</title>

            </head>

            <body style="margin: 0 0 0 0">

                <!--Header for chat room-->
                <h1>

                    <section>

                        <header style="margin-top: 0" class="header">

                            Chat Room

                        </header>

                    </section>
                    Chat room name :
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
                                <xsl:value-of select="LastName"></xsl:value-of>
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

                <section>
                    <header style="margin-bottom:0" class="header">

                        Messages history

                    </header>
                </section>

                <table style="background: white; width:100% ;padding:0 0 0 0 ; margin: 0 0 0 0;">

                    <xsl:for-each select="chatRoom/Message">

                        <tr>
                            <td>
                                <section style="margin-bottom:15px; margin-right:10px ;margin-left:10px">

                                    <xsl:choose>

                                        <xsl:when test="inRightDirection = 'true'">

                                            <xsl:attribute name="class">rightDirCallout</xsl:attribute>


                                        </xsl:when>

                                        <xsl:otherwise>

                                            <xsl:attribute name="class">leftDirCallout</xsl:attribute>

                                        </xsl:otherwise>

                                    </xsl:choose>

                                    <h4 style="color: #a2a2a2; margin-bottom: 5px">

                                        <xsl:value-of select="Sender/FirstName"> </xsl:value-of> &#160;

                                        <xsl:value-of select="Sender/LastName"></xsl:value-of>

                                    </h4>

                                    <span class="messageClass">

                                        <xsl:attribute name="style">

                                            background :
                                            <xsl:value-of select="bubbleColor"></xsl:value-of>;

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

                                    </span>

                                </section>

                            </td>

                        </tr>

                    </xsl:for-each>

                </table>

                <style>

                    [class~="rightDirCallout"] > span{

                        border-radius: 25px 0px 25px 25px;
                        float: right;
                        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                        overflow-wrap: normal;


                    }

                    [class~="leftDirCallout"] > span{

                        border-radius: 0px 25px 25px 25px;
                        float: left;
                        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                        overflow-wrap: normal;

                    }

                    section span{

                        display: block;
                        height: fit-content;
                        word-break: break-word;

                    }

                    .messageClass {

                        width: fit-content;
                        padding: 20px 30px 20px 30px;
                        color: white;
                        max-width: 85%;

                    }

                    .rightDirCallout {

                        text-align:right;
                        float: right;

                    }

                    .leftDirCallout {

                        text-align: left;
                        float: left;

                    }

                    .messageClass:hover{

                      opacity: 0.9;

                    }

                    .header {

                        text-align: center;
                        margin-top: 30px;
                        margin-bottom: 30px;
                        width: 100%;
                        background: #4c84ff;
                        padding: 20px 0px 20px 0px;
                        font-size: 30px;
                        color: #ffffff;
                        font-weight: bold;

                    }

                    body {

                         cursor: default;

                    }

                    th {

                        background: #4c84ff;
                        padding: 10px 10px 10px 10px;

                    }

                    table {

                        background-color: #ebebeb;
                        border-radius: 5px;

                    }

                    .head {

                        text-decoration: underline;
                        color: #4c84ff;

                    }

                </style>

            </body>

        </html>

    </xsl:template>

</xsl:stylesheet>
