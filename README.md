# googlePositionsToMyPositions

Il faut recuperer depuis le fichier kml les données suivantes uniquement si Placemark/Point n'est pas nul (on ignore les LineString):

- Placemark/name
- Placemark/address
- Placemark/ExtendedData/Data@Category
- Placemark/description
- Placemark/Point/coordinates
- Placemark/TimeSpan/begin
- Placemark/TimeSpan/end


La creation des POJO a été effectuée avec xjc avec le bidings suivants :



<jaxb:bindings
    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    xmlns:jaxb="http://java.sun.com/xml/ns/jaxb"
    version="2.1">
    <jaxb:globalBindings localScoping="toplevel"/>
</jaxb:bindings>

sur

<?xml version="1.0" encoding="utf-8"?>
<!-- Created with Liquid Technologies Online Tools 1.0 (https://www.liquid-technologies.com) -->
<xs:schema xmlns:gx="http://www.google.com/kml/ext/2.2" attributeFormDefault="unqualified" elementFormDefault="qualified" targetNamespace="http://www.opengis.net/kml/2.2" xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="kml">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="Document">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="name" type="xs:string" />
              <xs:element name="open" type="xs:unsignedByte" />
              <xs:element name="description" />
              <xs:element name="StyleMap">
                <xs:complexType>
                  <xs:sequence>
                    <xs:element maxOccurs="unbounded" name="Pair">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element name="key" type="xs:string" />
                          <xs:element name="styleUrl" type="xs:string" />
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                  </xs:sequence>
                  <xs:attribute name="id" type="xs:string" use="required" />
                </xs:complexType>
              </xs:element>
              <xs:element maxOccurs="unbounded" name="Style">
                <xs:complexType>
                  <xs:sequence>
                    <xs:element name="IconStyle">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element minOccurs="0" name="scale" type="xs:decimal" />
                          <xs:element name="Icon">
                            <xs:complexType>
                              <xs:sequence>
                                <xs:element name="href" type="xs:string" />
                              </xs:sequence>
                            </xs:complexType>
                          </xs:element>
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                    <xs:element name="LineStyle">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element name="color" type="xs:string" />
                          <xs:element name="width" type="xs:unsignedByte" />
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                  </xs:sequence>
                  <xs:attribute name="id" type="xs:string" use="required" />
                </xs:complexType>
              </xs:element>
              <xs:element maxOccurs="unbounded" name="Placemark">
                <xs:complexType>
                  <xs:sequence>
                    <xs:element name="name" type="xs:string" />
                    <xs:element name="address" type="xs:string" />
                    <xs:element name="ExtendedData">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element maxOccurs="unbounded" name="Data">
                            <xs:complexType>
                              <xs:sequence>
                                <xs:element name="value" type="xs:string" />
                              </xs:sequence>
                              <xs:attribute name="name" type="xs:string" use="required" />
                            </xs:complexType>
                          </xs:element>
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                    <xs:element name="description" type="xs:string" />
                    <xs:element minOccurs="0" name="LineString">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element name="altitudeMode" type="xs:string" />
                          <xs:element name="extrude" type="xs:unsignedByte" />
                          <xs:element name="tesselate" type="xs:unsignedByte" />
                          <xs:element name="coordinates" type="xs:string" />
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                    <xs:element minOccurs="0" name="Point">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element name="coordinates" type="xs:string" />
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                    <xs:element name="TimeSpan">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element name="begin" type="xs:dateTime" />
                          <xs:element name="end" type="xs:dateTime" />
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                  </xs:sequence>
                </xs:complexType>
              </xs:element>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>


et les inserer dasn DynamoDB Positions
