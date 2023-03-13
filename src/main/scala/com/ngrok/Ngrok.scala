package com.ngrok

import com.ngrok.services._
import java.util.Objects.requireNonNull
import scala.concurrent.ExecutionContext

/** Factory for [[Ngrok]] instances. */
object Ngrok {

  /** Creates a new ngrok API instance.
    *
    * @param apiClient an instance of [[NgrokApiClient]]
    */
  def apply(apiClient: NgrokApiClient)(implicit ec: ExecutionContext): Ngrok = new Ngrok(
    requireNonNull(apiClient, "apiClient is required")
  )

  /** Creates a new ngrok API instance using the default API client.
    *
    * @param apiKey API key used to access the ngrok API
    * @return an ngrok API instance
    */
  def apply(apiKey: String)(implicit ec: ExecutionContext): Ngrok = apply(
    DefaultNgrokApiClient(requireNonNull(apiKey, "apiKey is required"))
  )

  /** Creates a new ngrok API instance using the default API client.
    *
    * The API key is pulled from the environment variable <code>NGROK_API_KEY</code>.
    *
    * @return an ngrok API instance
    */
  def apply()(implicit ec: ExecutionContext): Ngrok = apply(
    requireNonNull(System.getenv("NGROK_API_KEY"), "NGROK_API_KEY is not set in the environment")
  )
}

/** Main entry point for the ngrok API. */
class Ngrok private (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {

  /** Abuse Reports allow you to submit take-down requests for URLs hosted by
    *  ngrok that violate ngrok&#39;s terms of service.
    *
    * See also <a href="https://ngrok.com/docs/api#api-abuse-reports">https://ngrok.com/docs/api#api-abuse-reports</a>.
    *
    * @return a service client
    */
  lazy val abuseReports: AbuseReports = new AbuseReports(this.apiClient)

  /** Creates a service client for AgentIngresses.
    *
    * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses">https://ngrok.com/docs/api#api-agent-ingresses</a>.
    *
    * @return a service client
    */
  lazy val agentIngresses: AgentIngresses = new AgentIngresses(this.apiClient)

  /** API Keys are used to authenticate to the <a
    * href="https://ngrok.com/docs/api#authentication">ngrok
    *  API</a>. You may use the API itself
    *  to provision and manage API Keys but you&#39;ll need to provision your first
    * API
    *  key from the <a href="https://dashboard.ngrok.com/api/keys">API Keys page</a>
    * on your
    *  ngrok.com dashboard.
    *
    * See also <a href="https://ngrok.com/docs/api#api-api-keys">https://ngrok.com/docs/api#api-api-keys</a>.
    *
    * @return a service client
    */
  lazy val apiKeys: ApiKeys = new ApiKeys(this.apiClient)

  /** Creates a service client for ApplicationSessions.
    *
    * See also <a href="https://ngrok.com/docs/api#api-application-sessions">https://ngrok.com/docs/api#api-application-sessions</a>.
    *
    * @return a service client
    */
  lazy val applicationSessions: ApplicationSessions = new ApplicationSessions(this.apiClient)

  /** Creates a service client for ApplicationUsers.
    *
    * See also <a href="https://ngrok.com/docs/api#api-application-users">https://ngrok.com/docs/api#api-application-users</a>.
    *
    * @return a service client
    */
  lazy val applicationUsers: ApplicationUsers = new ApplicationUsers(this.apiClient)

  /** Certificate Authorities are x509 certificates that are used to sign other
    *  x509 certificates. Attach a Certificate Authority to the Mutual TLS module
    *  to verify that the TLS certificate presented by a client has been signed by
    *  this CA. Certificate Authorities  are used only for mTLS validation only and
    *  thus a private key is not included in the resource.
    *
    * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities">https://ngrok.com/docs/api#api-certificate-authorities</a>.
    *
    * @return a service client
    */
  lazy val certificateAuthorities: CertificateAuthorities = new CertificateAuthorities(this.apiClient)

  /** Tunnel Credentials are ngrok agent authtokens. They authorize the ngrok
    *  agent to connect the ngrok service as your account. They are installed with
    *  the <code>ngrok config add-authtoken</code> command or by specifying it in the
    * <code>ngrok.yml</code>
    *  configuration file with the <code>authtoken</code> property.
    *
    * See also <a href="https://ngrok.com/docs/api#api-credentials">https://ngrok.com/docs/api#api-credentials</a>.
    *
    * @return a service client
    */
  lazy val credentials: Credentials = new Credentials(this.apiClient)

  /** Endpoints provides an API for querying the endpoint objects
    *  which define what tunnel or edge is used to serve a hostport.
    *  Only active endpoints associated with a tunnel or backend are returned.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoints">https://ngrok.com/docs/api#api-endpoints</a>.
    *
    * @return a service client
    */
  lazy val endpoints: Endpoints = new Endpoints(this.apiClient)

  /** Creates a service client for EventDestinations.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-destinations">https://ngrok.com/docs/api#api-event-destinations</a>.
    *
    * @return a service client
    */
  lazy val eventDestinations: EventDestinations = new EventDestinations(this.apiClient)

  /** Creates a service client for EventSubscriptions.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions">https://ngrok.com/docs/api#api-event-subscriptions</a>.
    *
    * @return a service client
    */
  lazy val eventSubscriptions: EventSubscriptions = new EventSubscriptions(this.apiClient)

  /** Creates a service client for EventSources.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-sources">https://ngrok.com/docs/api#api-event-sources</a>.
    *
    * @return a service client
    */
  lazy val eventSources: EventSources = new EventSources(this.apiClient)

  /** IP Policies are reusable groups of CIDR ranges with an <code>allow</code> or
    * <code>deny</code>
    *  action. They can be attached to endpoints via the Endpoint Configuration IP
    *  Policy module. They can also be used with IP Restrictions to control source
    *  IP ranges that can start tunnel sessions and connect to the API and dashboard.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policies">https://ngrok.com/docs/api#api-ip-policies</a>.
    *
    * @return a service client
    */
  lazy val ipPolicies: IpPolicies = new IpPolicies(this.apiClient)

  /** IP Policy Rules are the IPv4 or IPv6 CIDRs entries that
    *  make up an IP Policy.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules">https://ngrok.com/docs/api#api-ip-policy-rules</a>.
    *
    * @return a service client
    */
  lazy val ipPolicyRules: IpPolicyRules = new IpPolicyRules(this.apiClient)

  /** An IP restriction is a restriction placed on the CIDRs that are allowed to
    *  initiate traffic to a specific aspect of your ngrok account. An IP
    *  restriction has a type which defines the ingress it applies to. IP
    *  restrictions can be used to enforce the source IPs that can make API
    *  requests, log in to the dashboard, start ngrok agents, and connect to your
    *  public-facing endpoints.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions">https://ngrok.com/docs/api#api-ip-restrictions</a>.
    *
    * @return a service client
    */
  lazy val ipRestrictions: IpRestrictions = new IpRestrictions(this.apiClient)

  /** Reserved Addresses are TCP addresses that can be used to listen for traffic.
    *  TCP address hostnames and ports are assigned by ngrok, they cannot be
    *  chosen.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs">https://ngrok.com/docs/api#api-reserved-addrs</a>.
    *
    * @return a service client
    */
  lazy val reservedAddrs: ReservedAddrs = new ReservedAddrs(this.apiClient)

  /** Reserved Domains are hostnames that you can listen for traffic on. Domains
    *  can be used to listen for http, https or tls traffic. You may use a domain
    *  that you own by creating a CNAME record specified in the returned resource.
    *  This CNAME record points traffic for that domain to ngrok&#39;s edge servers.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains">https://ngrok.com/docs/api#api-reserved-domains</a>.
    *
    * @return a service client
    */
  lazy val reservedDomains: ReservedDomains = new ReservedDomains(this.apiClient)

  /** An SSH Certificate Authority is a pair of an SSH Certificate and its private
    *  key that can be used to sign other SSH host and user certificates.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities">https://ngrok.com/docs/api#api-ssh-certificate-authorities</a>.
    *
    * @return a service client
    */
  lazy val sshCertificateAuthorities: SshCertificateAuthorities = new SshCertificateAuthorities(this.apiClient)

  /** SSH Credentials are SSH public keys that can be used to start SSH tunnels
    *  via the ngrok SSH tunnel gateway.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials">https://ngrok.com/docs/api#api-ssh-credentials</a>.
    *
    * @return a service client
    */
  lazy val sshCredentials: SshCredentials = new SshCredentials(this.apiClient)

  /** SSH Host Certificates along with the corresponding private key allows an SSH
    *  server to assert its authenticity to connecting SSH clients who trust the
    *  SSH Certificate Authority that was used to sign the certificate.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates">https://ngrok.com/docs/api#api-ssh-host-certificates</a>.
    *
    * @return a service client
    */
  lazy val sshHostCertificates: SshHostCertificates = new SshHostCertificates(this.apiClient)

  /** SSH User Certificates are presented by SSH clients when connecting to an SSH
    *  server to authenticate their connection. The SSH server must trust the SSH
    *  Certificate Authority used to sign the certificate.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates">https://ngrok.com/docs/api#api-ssh-user-certificates</a>.
    *
    * @return a service client
    */
  lazy val sshUserCertificates: SshUserCertificates = new SshUserCertificates(this.apiClient)

  /** TLS Certificates are pairs of x509 certificates and their matching private
    *  key that can be used to terminate TLS traffic. TLS certificates are unused
    *  until they are attached to a Domain. TLS Certificates may also be
    *  provisioned by ngrok automatically for domains on which you have enabled
    *  automated certificate provisioning.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-certificates">https://ngrok.com/docs/api#api-tls-certificates</a>.
    *
    * @return a service client
    */
  lazy val tlsCertificates: TlsCertificates = new TlsCertificates(this.apiClient)

  /** Tunnel Sessions represent instances of ngrok agents or SSH reverse tunnel
    *  sessions that are running and connected to the ngrok service. Each tunnel
    *  session can include one or more Tunnels.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions">https://ngrok.com/docs/api#api-tunnel-sessions</a>.
    *
    * @return a service client
    */
  lazy val tunnelSessions: TunnelSessions = new TunnelSessions(this.apiClient)

  /** Tunnels provide endpoints to access services exposed by a running ngrok
    *  agent tunnel session or an SSH reverse tunnel session.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnels">https://ngrok.com/docs/api#api-tunnels</a>.
    *
    * @return a service client
    */
  lazy val tunnels: Tunnels = new Tunnels(this.apiClient)

  /** Creates a namespace object for Backends.
    *
    * @return a namespace object
    */
  lazy val backends: BackendsNamespace = new BackendsNamespace

  /** Creates a namespace object for Edges.
    *
    * @return a namespace object
    */
  lazy val edges: EdgesNamespace = new EdgesNamespace

  /** Creates a namespace object for EdgeModules.
    *
    * @return a namespace object
    */
  lazy val edgeModules: EdgeModulesNamespace = new EdgeModulesNamespace

  /** A namespace object for Backends. */
  class BackendsNamespace private[ngrok] () {

    /** A Failover backend defines failover behavior within a list of referenced
      *  backends. Traffic is sent to the first backend in the list. If that backend
      *  is offline or no connection can be established, ngrok attempts to connect to
      *  the next backend in the list until one is successful.
      *
      * See also <a href="https://ngrok.com/docs/api#api-failover-backends">https://ngrok.com/docs/api#api-failover-backends</a>
      *
      * @return a service client
      */
    lazy val failover: FailoverBackends = new FailoverBackends(apiClient)

    /** Creates a service client for [[services.HttpResponseBackends]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-http-response-backends">https://ngrok.com/docs/api#api-http-response-backends</a>
      *
      * @return a service client
      */
    lazy val httpResponse: HttpResponseBackends = new HttpResponseBackends(apiClient)

    /** A Tunnel Group Backend balances traffic among all online tunnels that match
      *  a label selector.
      *
      * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends">https://ngrok.com/docs/api#api-tunnel-group-backends</a>
      *
      * @return a service client
      */
    lazy val tunnelGroup: TunnelGroupBackends = new TunnelGroupBackends(apiClient)

    /** A Weighted Backend balances traffic among the referenced backends. Traffic
      *  is assigned proportionally to each based on its weight. The percentage of
      *  traffic is calculated by dividing a backend&#39;s weight by the sum of all
      *  weights.
      *
      * See also <a href="https://ngrok.com/docs/api#api-weighted-backends">https://ngrok.com/docs/api#api-weighted-backends</a>
      *
      * @return a service client
      */
    lazy val weighted: WeightedBackends = new WeightedBackends(apiClient)

  }

  /** A namespace object for Edges. */
  class EdgesNamespace private[ngrok] () {

    /** Creates a service client for [[services.EdgesHttpsRoutes]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edges-https-routes">https://ngrok.com/docs/api#api-edges-https-routes</a>
      *
      * @return a service client
      */
    lazy val httpsRoutes: EdgesHttpsRoutes = new EdgesHttpsRoutes(apiClient)

    /** Creates a service client for [[services.EdgesHttps]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edges-https">https://ngrok.com/docs/api#api-edges-https</a>
      *
      * @return a service client
      */
    lazy val https: EdgesHttps = new EdgesHttps(apiClient)

    /** Creates a service client for [[services.EdgesTcp]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edges-tcp">https://ngrok.com/docs/api#api-edges-tcp</a>
      *
      * @return a service client
      */
    lazy val tcp: EdgesTcp = new EdgesTcp(apiClient)

    /** Creates a service client for [[services.EdgesTls]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edges-tls">https://ngrok.com/docs/api#api-edges-tls</a>
      *
      * @return a service client
      */
    lazy val tls: EdgesTls = new EdgesTls(apiClient)

  }

  /** A namespace object for EdgeModules. */
  class EdgeModulesNamespace private[ngrok] () {

    /** Creates a service client for [[services.HttpsEdgeMutualTlsModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-https-edge-mutual-tls-module">https://ngrok.com/docs/api#api-https-edge-mutual-tls-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeMutualTls: HttpsEdgeMutualTlsModule = new HttpsEdgeMutualTlsModule(apiClient)

    /** Creates a service client for [[services.HttpsEdgeTlsTerminationModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-https-edge-tls-termination-module">https://ngrok.com/docs/api#api-https-edge-tls-termination-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeTlsTermination: HttpsEdgeTlsTerminationModule = new HttpsEdgeTlsTerminationModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteBackendModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-backend-module">https://ngrok.com/docs/api#api-edge-route-backend-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteBackend: EdgeRouteBackendModule = new EdgeRouteBackendModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteIpRestrictionModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-ip-restriction-module">https://ngrok.com/docs/api#api-edge-route-ip-restriction-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteIpRestriction: EdgeRouteIpRestrictionModule = new EdgeRouteIpRestrictionModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteRequestHeadersModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-request-headers-module">https://ngrok.com/docs/api#api-edge-route-request-headers-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteRequestHeaders: EdgeRouteRequestHeadersModule = new EdgeRouteRequestHeadersModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteResponseHeadersModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-response-headers-module">https://ngrok.com/docs/api#api-edge-route-response-headers-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteResponseHeaders: EdgeRouteResponseHeadersModule = new EdgeRouteResponseHeadersModule(
      apiClient
    )

    /** Creates a service client for [[services.EdgeRouteCompressionModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-compression-module">https://ngrok.com/docs/api#api-edge-route-compression-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteCompression: EdgeRouteCompressionModule = new EdgeRouteCompressionModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteCircuitBreakerModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module">https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteCircuitBreaker: EdgeRouteCircuitBreakerModule = new EdgeRouteCircuitBreakerModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteWebhookVerificationModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-webhook-verification-module">https://ngrok.com/docs/api#api-edge-route-webhook-verification-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteWebhookVerification: EdgeRouteWebhookVerificationModule =
      new EdgeRouteWebhookVerificationModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteOAuthModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-o-auth-module">https://ngrok.com/docs/api#api-edge-route-o-auth-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteOauth: EdgeRouteOAuthModule = new EdgeRouteOAuthModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteSamlModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-saml-module">https://ngrok.com/docs/api#api-edge-route-saml-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteSaml: EdgeRouteSamlModule = new EdgeRouteSamlModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteOidcModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-oidc-module">https://ngrok.com/docs/api#api-edge-route-oidc-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteOidc: EdgeRouteOidcModule = new EdgeRouteOidcModule(apiClient)

    /** Creates a service client for [[services.EdgeRouteWebsocketTcpConverterModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-edge-route-websocket-tcp-converter-module">https://ngrok.com/docs/api#api-edge-route-websocket-tcp-converter-module</a>
      *
      * @return a service client
      */
    lazy val httpsEdgeRouteWebsocketTcpConverter: EdgeRouteWebsocketTcpConverterModule =
      new EdgeRouteWebsocketTcpConverterModule(apiClient)

    /** Creates a service client for [[services.TcpEdgeBackendModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-tcp-edge-backend-module">https://ngrok.com/docs/api#api-tcp-edge-backend-module</a>
      *
      * @return a service client
      */
    lazy val tcpEdgeBackend: TcpEdgeBackendModule = new TcpEdgeBackendModule(apiClient)

    /** Creates a service client for [[services.TcpEdgeIpRestrictionModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-tcp-edge-ip-restriction-module">https://ngrok.com/docs/api#api-tcp-edge-ip-restriction-module</a>
      *
      * @return a service client
      */
    lazy val tcpEdgeIpRestriction: TcpEdgeIpRestrictionModule = new TcpEdgeIpRestrictionModule(apiClient)

    /** Creates a service client for [[services.TlsEdgeBackendModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-tls-edge-backend-module">https://ngrok.com/docs/api#api-tls-edge-backend-module</a>
      *
      * @return a service client
      */
    lazy val tlsEdgeBackend: TlsEdgeBackendModule = new TlsEdgeBackendModule(apiClient)

    /** Creates a service client for [[services.TlsEdgeIpRestrictionModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-tls-edge-ip-restriction-module">https://ngrok.com/docs/api#api-tls-edge-ip-restriction-module</a>
      *
      * @return a service client
      */
    lazy val tlsEdgeIpRestriction: TlsEdgeIpRestrictionModule = new TlsEdgeIpRestrictionModule(apiClient)

    /** Creates a service client for [[services.TlsEdgeMutualTlsModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module">https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module</a>
      *
      * @return a service client
      */
    lazy val tlsEdgeMutualTls: TlsEdgeMutualTlsModule = new TlsEdgeMutualTlsModule(apiClient)

    /** Creates a service client for [[services.TlsEdgeTlsTerminationModule]].
      *
      * See also <a href="https://ngrok.com/docs/api#api-tls-edge-tls-termination-module">https://ngrok.com/docs/api#api-tls-edge-tls-termination-module</a>
      *
      * @return a service client
      */
    lazy val tlsEdgeTlsTermination: TlsEdgeTlsTerminationModule = new TlsEdgeTlsTerminationModule(apiClient)

  }
}
